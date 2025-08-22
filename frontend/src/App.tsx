import { Select, MenuItem, FormControl, InputLabel, Box, Typography, Paper } from '@mui/material';
import { Button } from './components/ui/Button';
import { Plus } from 'lucide-react';
import { Modal } from './components/ui/modal';
import { StatusBadge } from './components/ui/StatusBadge';
import { useState } from 'react';
import type { JobApplication } from './types';

function App() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <div className="p-8">
      <h1 className="text-2xl font-bold mb-4">UI Components</h1>
      <div className="mb-6">
        <Button onClick={() => setIsModalOpen(true)}>
          Open Modal
        </Button>
      </div>
      <div
      className="mb-5"
      >
        <Button onClick={() => setIsModalOpen(true)}> 2 Modal </Button>
      </div>
      <Paper elevation={2} sx={{ p: 3, mb: 4 }}>
        <Typography variant="h6" gutterBottom>Status Badge Examples</Typography>
        <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap', mb: 4 }}>
          {(['Saved', 'Applied', 'Interview', 'Offer', 'Rejected'] as JobApplication['status'][]).map((status) => (
            <Box key={status} sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
              <StatusBadge status={status} />
              <Typography variant="body2">- {status}</Typography>
            </Box>
          ))}
        </Box>
      </Paper>

      <div className="space-y-4">
        <Typography variant="h6" gutterBottom>Button Examples</Typography>
        <Button>Default Button</Button>
        <Button variant="outlined">Outlined</Button>
        <Button loading>Loading</Button>
        <Button icon={Plus}>Add</Button>
        <Button icon={Plus} variant="outlined">Add</Button>
        <FormControl fullWidth>
          <InputLabel>Gender</InputLabel>
          <Select
            label="Gender"
            value=""
            onChange={(e) => console.log(e.target.value)}
          >
            {[
              { value: 'option1', label: 'Option 1' },
              { value: 'option2', label: 'Option 2' },
              { value: 'option3', label: 'Option 3' },
            ].map((option) => (
              <MenuItem key={option.value} value={option.value}>
                {option.label}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </div>
      
      <Modal 
        isOpen={isModalOpen} 
        onClose={() => setIsModalOpen(false)}
        title="Test Modal"
      >
        <div className="p-4">
          <h3 className="text-lg font-medium mb-4">Modal Content</h3>
          <p>This is a test modal. You can add any content here.</p>
        </div>
      </Modal>
    </div>
  );
}

export default App;