import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

export function Settings() {
  return (
    <section className="h-full w-full p-6 overflow-auto relative">
      <h1 className="text-3xl font-semibold">Configuración</h1>
      <section className="mt-4">
        <p className="text-gray-600 font-semibold p-5">
          Aquí puedes ajustar la configuración de tu cuenta.
        </p>
      </section>
      <section>
        <div className="bg-white shadow-md rounded-lg p-6 mb-6">
          <h2 className="text-xl font-semibold mb-4">Perfil</h2>
          <form>
            <div className="mb-4">
              <label className="block text-gray-700">Nombre de usuario</label>
              <Input
                type="text"
                className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-blue-500 focus:ring-blue-500"
                placeholder="Tu nombre de usuario"
              />
            </div>
            <div className="mb-4">
              <label className="block text-gray-700">Correo electrónico</label>
              <Input
                type="email"
                className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-blue-500 focus:ring-blue-500"
                placeholder="Tu correo electrónico"
              />
            </div>
            <Button
              type="submit"
            >
              Guardar cambios
            </Button>
          </form>
        </div>

        <div className="bg-white shadow-md rounded-lg p-6">
          <h2 className="text-xl font-semibold mb-4">Seguridad</h2>
          <p className="text-gray-600 mb-4">
            Cambia tu contraseña o activa la autenticación de dos factores.
          </p>
          <Button
            className="px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700 transition-colors"
          >
            Cambiar contraseña
          </Button>
        </div>
      </section>
    </section>
  );
}