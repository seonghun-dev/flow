import React from 'react';
import {IExtension} from "../../type/IExtension";
import './CustomExtension.css';

interface CustomExtensionProps {
    extension: IExtension;
    onDelete: (
        id: number
    ) => void;
}

const CustomExtension: React.FC<CustomExtensionProps> = ({extension, onDelete}) => {
    const handleDelete = () => {
        onDelete(extension.id);
    };

    return (
        <div
            className="custom-extension-item">
            <span className="custom-extension-name">{extension.name}</span>
            <button
                className="custom-extension-delete-button"
                onClick={handleDelete}>X
            </button>
        </div>
    );
};

export default CustomExtension;
