import React  from "react";

/**
 * A StatusBadge represents a colored badge that displays a job application's status.
 * It is a wrapper around Material-UI's Chip component.
 */

import { Chip } from "@mui/material";
import type { JobApplication } from "../types";

interface StatusBadgeProps {
    status: JobApplication["status"];
}

export const StatusBadge : React.FC<StatusBadgeProps> = ({status}) => {
 
    const statusConfig =  {
       Saved:{color:"default" as const , variant:"outlined" as const},
       Applied:{color:"primary" as const , variant:"filled" as const},
       Interview:{color:"warning" as const , variant:"filled" as const},
       Offer:{color:"success" as const , variant:"filled" as const},
       Rejected:{color:"error" as const , variant:"filled" as const} 
};

const config = statusConfig[status];

return (
    <Chip
        label={status}
        color={config.color}
        variant={config.variant}
        size="small"
    />
);
};
