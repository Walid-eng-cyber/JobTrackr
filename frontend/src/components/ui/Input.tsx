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


/**
 * This is a functional component that takes in props of type InputProps.
 * The React.forwardRef function is used to pass a ref to the underlying input element.
 * This is useful when you need to imperatively interact with the input element.
 * 
 * The function returns a TextField component from Material-UI.
 * The TextField component is a form control that allows users to input text.
 * It accepts many props such as label, error, helperText, type, placeholder, value, onChange, disabled, fullWidth, multiline, rows, name, and required.
 * 
 * The props are passed to the TextField component and are used to customize its appearance and behavior.
 * For example, the label prop is used to display a label above the input field.
 * The error prop is used to display an error message below the input field.
 * The type prop is used to specify the type of input field, such as text, password, email, etc.
 * The placeholder prop is used to display a placeholder text inside the input field.
 * The value prop is used to set the initial value of the input field.
 * The onChange prop is a function that is called when the value of the input field changes.
 * The disabled prop is used to disable the input field, making it uninteractive.
 * The fullWidth prop is used to make the input field take up the full width of its container.
 * The multiline prop is used to make the input field a multiline textarea.
 * The rows prop is used to specify the number of rows in a multiline input field.
 * The name prop is used to specify the name of the input field.
 * The required prop is used to make the input field required.
 * 
 * The ref prop is used to pass a ref to the underlying input element.
 * This allows you to imperatively interact with the input element, such as focusing on it or selecting its text.
 * 
 * The Input component is a reusable component that can be used in many different parts of your application.
 * It provides a consistent and customizable input field for users to input text.
 * It also provides a way to validate input and display error messages.
 */

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