"use client"
import { Player } from "@/models/player.model";
import { getPlayer } from "@/service/player.service";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function PlayerPage() {
    
    const { id } = useParams<{ id: string }>();
    
    const [player, setPlayer] = useState<Player | undefined>();

    useEffect(() => {
        getPlayer(id)
            .then((player) => setPlayer(player));
    }, [id])

    if(!player){
        return (
            <p>Cargando...</p>
        )
    } else {
        return (
            <div className="bg">
                <header className="container bg-gray-800 p-10 rounded-4xl flex flex-wrap md:flex-nowrap items-center gap-10 justify-center lg:justify-start">
                    <img src={"https://cdn.brawlify.com/profile-icons/regular/" + player.iconId + ".png"} className="w-50"/>
                    <div>
                        <h1 className="text-4xl sm:text-5xl md:text-6xl lg:text-7xl xl:text-9xl">{player.name}</h1>
                        <h2 className="text-sm sm:text-xl md:text-2xl lg:text-3xl xl:text4xl">{player.trophies} trofeos</h2>
                        <p className="text-sm sm:text-md md:text-lg lg:text-xl xl:text-2xl">{player.clubName}</p>
                    </div>
                </header>
                <section className="container bg-gray-800 p-10 rounded-4xl justify-center lg:justify-start mt-5">
                    <h2 className="text-sm sm:text-2xl md:text-3xl lg:text-4xl xl:text5xl">Nada que ver por aquí</h2>
                    <p className="text-sm sm:text-sm md:text-md lg:text-lg xl:text-xl">¡Pronto esta página tendrá más información!</p>
                </section>
            </div>
        )    
    }
}