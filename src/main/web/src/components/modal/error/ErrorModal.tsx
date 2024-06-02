import React, {useState} from "react";
import './ErrorModal.css';

interface ErrorModalProps {
    title: string;
    message: string;
}

const ErrorModal: React.FC<ErrorModalProps> = ({title, message}) => {
    const [showModal, setShowModal] = useState(true);

    const closeModal = () => {
        setShowModal(false);
    };

    return (
        <>
            {showModal && (
                <div className="modal">
                    <div className="modal-content">
                        <img src="https://100dayscss.com/codepen/alert.png" width="44" height="38" alt=""/>
                        <p className="error-modal-title">{title}</p>
                        <p className="error-modal-message">{message}</p>
                        <button
                            className="error-modal-button"
                            onClick={closeModal}>확인
                        </button>
                    </div>
                </div>
            )}
        </>
    );
}

export default ErrorModal;
