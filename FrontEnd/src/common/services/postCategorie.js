import axiosInstance from "./api";
export async function postCategorieServices(data) {
    try {
        const response = await axiosInstance.post('category', data);
        return response.data;
    }
    catch (e) {
        console.log("Error al enviar la categoría:", e);
    }
}
