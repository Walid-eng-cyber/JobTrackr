import React from "react";
import TextField from "@mui/material/TextField";


/**
 * Interfaces in TypeScript are used to define the shape of objects. 
 * They provide a way to create contracts that ensure objects have certain properties and methods.
 * 
 * In this case, the InputProps interface is used to define the shape of the props object that can be passed to the Input component. 
 * This makes the component more reusable and easier to understand.
 * 
 * By using interfaces, we can ensure that the component is used correctly and that all required props are provided.
 */

interface InputProps {
    label?: string;
    error?: string;
    helperText?: string;
    type?: string;
    placeholder?: string;
    value?: string;
    onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
    disabled?: boolean;
    fullWidth?: boolean;
    multiline?: boolean;
    rows?: number;
    name?: string;
    required?: boolean;
}

export const Input = React.forwardRef<HTMLInputElement, InputProps>(function Input(
  {
    label,
    error,
    helperText,
    type = "text",
    placeholder,
    value,
    onChange,
    disabled,
    fullWidth,
    multiline,
    rows,
    name,
    required,
  },
  ref
) {
  return (
    <TextField
      inputRef={ref}
      label={label}
      error={Boolean(error)}
      helperText={error ?? helperText}
      type={type}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      disabled={disabled}
      fullWidth={fullWidth}
      multiline={multiline}
      rows={rows}
      name={name}
      required={required}
    />
  );
});