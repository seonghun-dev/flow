import React, {useEffect, useState} from "react";
import ExtensionApi from "../api/ExtensionApi";
import {IExtension} from "../type/IExtension";
import FixedCheckBox from "../components/fixed/FixedCheckBox";

import CustomExtensionInput from "../components/custom/CustomExtensionInput";
import CustomExtension from "../components/custom/CustomExtension";
import ErrorModal from "../components/modal/error/ErrorModal";
import {IError} from "../type/IError";
import './FileExtension.css';


function FileExtension() {
    const [fixedExtensions, setFixedExtensions] = useState<IExtension[]>([]);
    const [customExtensions, setCustomExtensions] = useState<IExtension[]>([]);
    const customExtensionCountMax = 200;
    const [toast, setToast] = useState(false);
    const [errorMsg, setErrorMsg] = useState<IError>({
        errorResponseCode: '',
        title: '',
        message: '',
    });

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        await ExtensionApi.getExtension()
            .then(
                response => {
                    const extensions = response.data;
                    setFixedExtensions(extensions["fixedExtensionList"]);
                    setCustomExtensions(extensions["customExtensionList"]);
                }
            )
            .catch(handleError);
    }

    const handleError = (error: any) => {
        if (error.response.status >= 400 && error.response.status < 500) {
            setErrorMsg(error.response.data)
            setToast(true)
        }
        if (error.response.status >= 500) {
            setErrorMsg({
                errorResponseCode: '500',
                title: '서버 에러',
                message: '서버 에러가 발생했습니다. 잠시 후 다시 시도해주세요.'
            })
            setToast(true)
        }

    }

    const onCheck = async (id: number, isOn: boolean) => {
        await ExtensionApi.toggleExtension(id, isOn)
            .then(fetchData)
            .catch(handleError);
    }

    const onAdd = async (value: string) => {
        await ExtensionApi.createExtension(value)
            .then(fetchData)
            .catch(handleError);
    }

    const onDelete = async (id: number) => {
        await ExtensionApi.deleteExtension(id)
            .then(fetchData)
            .catch(handleError);
    }

    return (
        <div className="container">
            <h1>파일 확장자 차단</h1>
            <div className="fixed-extension-container">
                <div className="fixed-extension-items">
                    <h3 className="fixed-extension-title">고정 확장자</h3>
                    {fixedExtensions.map(ext => (
                        <FixedCheckBox extension={ext} key={ext.id} onCheck={onCheck}/>
                    ))}

                </div>
            </div>
            <div className="custom-extension-container">
                <div className="custom-extension-items">
                    <h3 className="custom-extension-title">커스텀 확장자</h3>
                    <div className="custom-extension-item-block">
                        <CustomExtensionInput onAdd={onAdd}/>
                        <div className="custom-extension-item-container">
                            <p className="custom-extension-item-count">{customExtensions.length} / {customExtensionCountMax}</p>
                            {customExtensions.map(ext => (
                                <CustomExtension extension={ext} onDelete={onDelete} key={ext.id}/>
                            ))}
                        </div>
                    </div>

                </div>
            </div>
            {toast && (
                <ErrorModal
                    title={errorMsg?.title}
                    message={errorMsg?.message}
                />
            )}
        </div>
    );

}

export default FileExtension;