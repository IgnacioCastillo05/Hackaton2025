import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { postRegister } from "./services/loginService";
import { toast } from "sonner";
import { appSaveUser } from "@/store/userStore";

export function Register() {
  const [formData, setFormData] = useState<{ email: string; password: string; nombre: string; confirmarPassword: string }>({
    email: "",
    password: "",
    nombre: "",
    confirmarPassword: ""
  });
  const mutation = useMutation({ 
    mutationFn: (data: { email: string; password: string; nombre: string; confirmarPassword: string }) => postRegister(data),
    onSuccess: (response) => {
      console.log(response);
      
      import { useState } from "react";
      import { useNavigate } from "react-router-dom";

      export function Register() {
        const navigate = useNavigate();
        const [nombre, setNombre] = useState("");
        const [email, setEmail] = useState("");
        const [password, setPassword] = useState("");

        const handleSubmit = (e: React.FormEvent) => {
          e.preventDefault();
          console.log("Register submit", { nombre, email, password });
          alert("Cuenta creada (demo): " + nombre);
          navigate('/');
        };

        return (
          <main className="flex items-center justify-center h-screen bg-gray-100">
            <div className="flex flex-col bg-white rounded-lg shadow-md w-96 p-6 py-14">
              <h2 className="text-2xl font-bold text-center text-black">Crear cuenta</h2>
              <form onSubmit={handleSubmit} className="flex flex-col justify-center h-full gap-5">
                <div>
                  <label className="block text-sm font-medium mb-2">Nombre</label>
                  <input value={nombre} onChange={(e) => setNombre(e.target.value)} className="border p-2 w-full" />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Email</label>
                  <input value={email} onChange={(e) => setEmail(e.target.value)} className="border p-2 w-full" />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Contraseña</label>
                  <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} className="border p-2 w-full" />
                </div>
                <button type="submit" className="bg-green-600 text-white p-2 rounded">Crear cuenta</button>
                <button type="button" onClick={() => navigate('/')} className="mt-2 border p-2 rounded">Iniciar sesión</button>
              </form>
            </div>
          </main>
        );
      }