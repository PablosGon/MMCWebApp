export default function ErrorComponent() {
    return (
        <div className="container bg-gray-800 p-10 rounded-4xl md:flex-nowrap items-center gap-10 justify-center lg:justify-start">
            <h1 className="text-4xl sm:text-5xl">Algo ha ido mal...</h1>
            <p className="text-sm sm:text-md md:text-lg lg:text-xl">Vuelve a intentarlo más tarde. Si el error persiste, consulta el error con el líder del club.</p>
        </div>
    )
}