import { Routes, Route } from "react-router";
import { Login } from "./pages/Authentication/Login";
import { Register } from "./pages/Authentication/Register";



export function App () {
  return (
      <Routes>
        <Route path="/" index element={<Login />} />
        <Route path="/register" index element={<Register />} />

      </Routes>
    )
}

