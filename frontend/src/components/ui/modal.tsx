import React from 'react';
import { Dialog, DialogTitle, DialogContent, IconButton } from '@mui/material';
import { X } from 'lucide-react';


/**
 * Modal component that displays a dialog with a title and content.
 * It provides a way to show additional information or perform actions in a separate window.
 *
 * @param {boolean} isOpen - Controls the visibility of the modal.
 * @param {() => void} onClose - Callback function to close the modal.
 * @param {string} title - The title of the modal.
 * @param {React.ReactNode} children - The content of the modal.
 * @param {'xs' | 'sm' | 'md' | 'lg' | 'xl'} [size='md'] - The size of the modal.
 */

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  title: string;
  children: React.ReactNode;
  size?: 'xs' | 'sm' | 'md' | 'lg' | 'xl';
}

export const Modal: React.FC<ModalProps> = ({
  isOpen,
  onClose,
  title,
  children,
  size = 'md'
}) => {
  return (
    <Dialog
      open={isOpen}
      onClose={onClose}
      maxWidth={size}
      fullWidth
      sx={{
        borderRadius: 2,
      }}
    >
      <DialogTitle sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        {title}
        <IconButton onClick={onClose} size="small">
          <X size={16} />
        </IconButton>
      </DialogTitle>
      <DialogContent>
        {children}
      </DialogContent>
    </Dialog>
  );
};
