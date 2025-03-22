"use client"
import { TrophyChart } from "@/components/player/trophy-chart.component";
import { Player } from "@/models/player.model";
import { getOrCreatePlayer } from "@/service/player.service";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function PlayerPage() {

    const { id } = useParams<{ id: string }>();
    
    const [player, setPlayer] = useState<Player | undefined>();

    useEffect(() => {
        const fetchPlayer = async() => {
            try {
                const data = await getOrCreatePlayer(id);
                setPlayer(data);    
            } catch (error) {

            }
        }
        fetchPlayer();
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
                        <h1 className="text-3xl sm:text-4xl md:text-5xl lg:text-7xl xl:text-7xl">{player.name}</h1>
                        <h2 className="text-xl sm:text-xl md:text-2xl lg:text-3xl xl:text4xl">{player.trophies} trofeos</h2>
                        <p className="text-lg sm:text-md md:text-lg lg:text-xl xl:text-2xl">{player.clubName}</p>
                    </div>
                </header>
                <div className="container flex flex-wrap gap-5">
                    <section className="container bg-gray-800 p-10 rounded-4xl justify-items-center lg:justify-start mt-5 flex-1/4">
                        <h2 className="text-2xl sm:text-2xl md:text-3xl">Progreso de temporada</h2>
                        <TrophyChart player={player}/>
                    </section>
                </div>
                
            </div>
        )    
    }
}