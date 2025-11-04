import { useState } from "react";
import { useNavigate } from "react-router-dom";

export function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Placeholder: envío local, aquí integrarás la API si quieres
    console.log("Login submit", { email, password });
    alert("Iniciando sesión (modo demo) con: " + email);
  };

  return (
    <main className="flex items-center justify-center h-screen bg-gray-100">
      <div className="flex flex-col bg-white rounded-lg shadow-md w-96 p-6 py-14">
        <h2 className="text-2xl font-bold text-center text-black">Iniciar sesión</h2>
        <form onSubmit={handleSubmit} className="flex flex-col justify-center h-full gap-5">
          <div>
            <label className="block text-sm font-medium mb-2" htmlFor="email">Email</label>
            <input id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Ingresa tu email" className="border p-2 w-full" />
          </div>
          <div>
            <label className="block text-sm font-medium mb-2" htmlFor="password">Contraseña</label>
            <input id="password" name="password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Ingresa tu contraseña" className="border p-2 w-full" />
          </div>

          <button type="submit" className="bg-blue-600 text-white p-2 rounded">Iniciar sesión</button>
          <button type="button" onClick={() => navigate('/register')} className="mt-2 border p-2 rounded">Crear cuenta</button>
        </form>
      </div>
    </main>
  );
}