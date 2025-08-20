import React from "react";
import {Button as MuiButton, CircularProgress} from "@mui/material";
import { Icon } from 'lucide-react';
import type { LucideIcon } from "lucide-react";

/**
 * Interface for Button component props.
 * It defines the props that can be passed to the Button component.
 * Each prop has a specific type and a description of what it does.
 * The props are used to customize the Button component and its behavior.
 */

interface ButtonProps {
    children: React.ReactNode;
    icon?: LucideIcon;
    variant?: "contained" | "outlined" | "text";

    /**
     * @see https://mui.com/system/basics/#makes-use-of-the-theme-object
     */
    sx?: Parameters<typeof MuiButton>[0]["sx"];
    fullWidth?: boolean;
    color?: "primary" | "secondary" | "error" | "warning" | "info" | "success";
    size?: "small" | "medium" | "large";
    disabled?: boolean;
    loading?: boolean;
    onClick?: () => void;
    type?: "button" | "submit" | "reset";
    className?: string;  
}


export const Button: React.FC<ButtonProps> = ({
    children,
    icon,
    variant = "contained",
    sx,
    color = "primary",
    size = "medium",
    disabled ,
    loading = false,
    onClick,
    type = "button",
    className = "",
    fullWidth = false,
    ...props
}) => {
    return (
        <MuiButton
      variant={variant}
      color={color}
      size={size}
      onClick={onClick}
      type={type}
      disabled={disabled || loading}
      fullWidth={fullWidth}
      className={className}
      startIcon={loading ? <CircularProgress size={16} /> : icon ? React.createElement(icon, { size: 16 }) : undefined}
      {...props}
    >
      {children}
    </MuiButton>
    );
};