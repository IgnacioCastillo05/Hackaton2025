import axiosInstance from "./api";
export async function deleteCategorieServices(id) {
    try {
        const response = await axiosInstance.delete(`/${id}`);
        return response.data;
    }
    catch (e) {
        console.log("Error al enviar la categoría:", e);
    }
}
