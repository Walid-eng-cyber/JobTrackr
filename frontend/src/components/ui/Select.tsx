import React from "react";
import { FormControl, InputLabel, Select as MuiSelect, MenuItem, FormHelperText } from "@mui/material";
import type { SelectChangeEvent } from "@mui/material/Select";



/**
 * Select is a component that renders a Material-UI Select with a label, optional error message, and options.
 * 
 * @param {Object} props - The properties for the Select component.
 * @param {string} [props.label] - The label for the Select component.
 * @param {string} [props.error] - The error message to display if the Select component is in an error state.
 * @param {string} [props.value] - The value of the Select component.
 * @param {function} [props.onChange] - The function to call when the Select component value changes.
 * @param {boolean} [props.disabled] - Whether the Select component is disabled.
 * @param {boolean} [props.fullWidth] - Whether the Select component should take up the full width of its container.
 * @param {number} [props.rows] - The number of rows for a multiline Select component.
 * @param {string} [props.name] - The name attribute for the Select component.
 * @param {boolean} [props.required] - Whether the Select component is required.
 * @param {Object[]} props.options - An array of objects representing the options for the Select component.
 * @param {string} props.options[].value - The value of an option.
 * @param {string} props.options[].label - The label for an option.
 */

interface SelectProps {
    label?: string;
    error?: string;
    value?: string;
    onChange?: (event: SelectChangeEvent<unknown>) => void;
    disabled?: boolean;
    fullWidth?: boolean;
    rows?: number;
    name?: string;
    required?: boolean;
    options: {value : string ; label : string}[];
}

export const Select = React.forwardRef<HTMLDivElement, SelectProps>(({ 
    label,
    error,
    value,
    onChange,
    disabled = false,
    fullWidth = false,
    rows,
    name,
    required = false,
    options,
    ...props
}, ref) => {
    return (
        <FormControl fullWidth={fullWidth} error={Boolean(error)}>
            <InputLabel>{label}</InputLabel>
            <MuiSelect
                value={value || ""}
                onChange={(e) => onChange?.(e as SelectChangeEvent<unknown>)}
                disabled={disabled}
                name={name}
                required={required}
                ref={ref}
                {...props}
            >
                {options.map((option) => (
                    <MenuItem key={option.value} value={option.value}>
                        {option.label}
                    </MenuItem>
                ))}
            </MuiSelect>
            {error && <FormHelperText>{error}</FormHelperText>}
        </FormControl>
    );
});
Select.displayName = "Select";