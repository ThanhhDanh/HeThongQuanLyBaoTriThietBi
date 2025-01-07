import React from 'react';
import { Alert } from 'react-bootstrap';

const MyAlert = ({ text, show, onClose }) => {
    return (
        <div>
            {show && (
                <Alert variant="warning" onClose={onClose} dismissible>
                    {text}
                </Alert>
            )}
        </div>
    );
};

export default MyAlert;
