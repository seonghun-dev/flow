import {axiosInstance} from "./AxionInstance";


const ExtensionApi = {
    getExtension: () => {
        return axiosInstance.get("/api/extensions");
    },
    createExtension: (extensionName: string) => {
        return axiosInstance.post("/api/extensions", {
            extensionName: extensionName
        });
    },
    deleteExtension: (extensionId: number) => {
        return axiosInstance.delete(`/api/extensions/${extensionId}`);
    },
    toggleExtension: (extensionId: number, isOn: boolean) => {
        return axiosInstance.patch(`/api/extensions/${extensionId}`,{
            isOn: isOn
        });
    }
}

export default ExtensionApi;