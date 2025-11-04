import { appGetUser, appSaveUser } from "@/store/userStore";
import type { User } from "../types/types";
import axiosInstance from "./api";

interface IsAuthResponse {
  message: string;
  data?: User;
}

export async function isAuth() {
  try {
    const { data } = await axiosInstance.get<IsAuthResponse>('auth/isAuthenticated', {
      headers: {
        'Content-Type': 'application/json',
        body: JSON.stringify(appGetUser())
      }
    });
    if (!data.message || data.message === 'Not auth') {
      return undefined;
    }

    //appSaveUser(data);
    return data;
  } catch (e) {
    console.log(e);
    return null;
  }
}