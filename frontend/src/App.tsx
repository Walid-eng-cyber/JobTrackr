import { Select, MenuItem, FormControl, InputLabel } from '@mui/material';
import { Button } from './components/ui/Button';
import { Plus } from 'lucide-react';

function App() {
  return (
    <div className="p-8">
      <h1 className="text-2xl font-bold mb-4">UI Components</h1>
      <div className="space-y-4">
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
      
    </div>
  );
}

export default App;