import { CLUBS } from "@/constants/clubs-names.constant";
import Image from "next/image";
import Link from "next/link";

export default function Home() {
  return (
    <div className="flex flex-col gap-5">
      <header className="bg-gray-800 rounded-2xl p-5 flex flex-col gap-2 items-center">
        <Image src={"/AppLogo_Beta.png"} width={300} height={200} alt="logo beta" className=""/>
        <h1 className="text-2xl text-center">
          ¡Bienvenido a la web de Mortis May Cry!
        </h1>
        <p className="text-sm text-center">
        ¡Más funciones están en camino! Esta aplicación aún está en desarrollo, por lo que es posible que puedas encontrar errores, en cuyo caso agradecemos que los reportes al líder del club para poder solucionarlos lo antes posible. ¡Muchas gracias!
        </p>
      </header>

      <section>
        <h2 className="text-2xl">
          Clubes
        </h2>
        <ul className="flex flex-wrap flex-col lg:flex-row gap-3 justify-center">
          {
            CLUBS.map((club, index) => (
              <li key={club.id} className="flex-1">
                <Link href={"/club/" + index}>
                  <div className="h-20 bg-gray-800 rounded-2xl text-center flex flex-row gap-2 flex-wrap pl-3 pr-3 items-center">
                    <Image src={"/clubs/" + index + ".png"} alt={club.name} width={100} height={100} />
                    <h2 className="text-xl">{club.name}</h2>
                    <p className="ml-auto">→</p>
                  </div>
                </Link>
              </li>
            ))
          }
        </ul>
      </section>

      <section>
        <h2 className="text-2xl">
          Jugadores estelares
        </h2>
        <Link href="/starplayers" className="bg-gray-800 rounded-2xl p-5 flex flex-col items-center">
          <div className="flex items-center">
            <div className="min-w-20">
              <Image src={"/star_players/star_legend.png"} width={200} height={200} alt="grand star player"/>
            </div>
            <div className="min-w-20">
              <Image src={"/star_players/grand_star_player.png"} width={200} height={200} alt="grand star player" className="min-w-20"/>
            </div>
            <div className="min-w-20">
              <Image src={"/star_players/star_master.png"} width={200} height={200} alt="grand star player" className="min-w-20"/>
            </div>
          </div>
          <h1 className="text-2xl text-center">
            Accede al estrellato
          </h1>
          <p className="text-sm text-center">
            Consulta quiénes han sido los mejores jugadores de cada temporada
          </p>
        </Link>
      </section>

      <footer className="text-end">
        <small className="text-end">
          MMC App by <Link href={"club/0/player/20CU8228G"} className="underline">Pablo Sánchez González</Link></small>
      </footer>
    </div>
  );
}
