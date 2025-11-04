import { Navigate, Outlet } from "react-router-dom";
import { HomeAside } from "./Aside";
import { TbMenu4 } from "react-icons/tb";
import useAuth from "@/store/userStore";
import { useState } from "react";

export function Layout() {
  const [isHidden, setisHidden] = useState(true);
  const { isAuth } = useAuth();

  if (!isAuth) {
    return <Navigate to="/" replace />;
  }
  return (
    <main className="flex flex-col h-screen bg-gray-100 w-full">
      <div className="flex flex-row w-full h-full">
        <HomeAside hidden={isHidden} />
        <div className="bg-white rounded-lg shadow-md w-full h-full">
          <nav className="flex items-center p-2 md:hidden bg-white w-full border-b border-gray-200">
            <TbMenu4 size={32} className="cursor-pointer" onClick={() => setisHidden(!isHidden)}/>
          </nav>
          <Outlet />
        </div>
      </div>
    </main>
  );
}