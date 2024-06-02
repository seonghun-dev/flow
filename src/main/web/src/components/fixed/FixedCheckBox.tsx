import React, {useEffect} from "react";
import {IExtension} from "../../type/IExtension";
import './FixedCheckBox.css';

interface FixedCheckBoxProps {
    extension: IExtension;
    onCheck: (id: number, isOn: boolean) => void;
}

const FixedCheckBox: React.FC<FixedCheckBoxProps> = ({extension, onCheck}) => {
    const [checked, setChecked] = React.useState(false);

    useEffect(() => {
        setChecked(extension.isOn);
    }, []);

    const handleChange = async (event: React.ChangeEvent<HTMLInputElement>) => {
        onCheck(extension.id, event.target.checked)
        setChecked(event.target.checked);
    };

    return (
        <label
            className="fixed-extension-item-label"
        >
            <input id={String(extension.id)}
                   className="fixed-extension-item-checkbox"
                   type="checkbox"
                   checked={checked}
                   onChange={handleChange}/>
            {extension.name}
        </label>
    );
};
export default FixedCheckBox;