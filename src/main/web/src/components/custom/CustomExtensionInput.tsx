import React, {useState} from 'react';
import './CustomExtensionInput.css';
interface InputWithButtonProps {
    onAdd: (value: string) => void;
}

const CustomExtensionInput: React.FC<InputWithButtonProps> = ({onAdd}) => {
    const [value, setValue] = useState<string>('');

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setValue(event.target.value);
    };

    const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            handleAdd();
        }
    }

    const handleAdd = () => {
        if (value.trim() !== '') {
            onAdd(value.trim());
            setValue('');
        }
    };

    return (
        <div
            className="custom-extension-input-container"
        >
            <input
                className="custom-extension-input"
                type="text"
                value={value}
                onChange={handleChange}
                onKeyDown={handleKeyDown}
                placeholder="확장자 입력"
            />
            <button
                className="custom-extension-add-button"
                onClick={handleAdd}>+ 추가</button>
        </div>
    );
};

export default CustomExtensionInput;
