"use client"
import { MegapigRegistriesComponent } from "@/components/player/megapig-registries.component";
import { PlayerAchievementsComponent } from "@/components/player/player-achievements.component";
import { TrophyChart } from "@/components/player/trophy-chart.component";
import ErrorComponent from "@/components/shared/error.component";
import LoadingComponent from "@/components/shared/loading.component";
import { Player } from "@/models/player.model";
import { playerService } from "@/service/player.service";
import Image from "next/image";
import Link from "next/link";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { Line } from "react-chartjs-2";

export default function PlayerPage() {

    const { playerid } = useParams<{ playerid: string }>();
    const { clubid } = useParams<{ clubid: string }>();
    
    const [player, setPlayer] = useState<Player | undefined>();
    const [error, setError] = useState<boolean>(false);

    useEffect(() => {
        const fetchPlayer = async() => {
            try {
                const data = await playerService.getOrCreatePlayer(playerid);
                console.log(Math.min(...data.trophyRegistries.map(tr => tr.trophies)), Math.max(...data.trophyRegistries.map(tr => tr.trophies)))
                setPlayer(data);    
            } catch (error) {
                console.log(error);
                setError(true);
            }
        }
        fetchPlayer();
    }, [playerid])

    console.log(player?.clubEventRegistries[0].dateTime)

    if (error) {
        return (
            <ErrorComponent/>
        )
    } else if (!player){
        return (
            <LoadingComponent/>
        )
    } else {
        return (
            <div className="bg">
                <Link href={`/club/${clubid}`} className="text-lg">← Volver</Link>
                <header className="container bg-gray-800 p-10 rounded-4xl flex flex-wrap md:flex-nowrap items-center gap-10 justify-center lg:justify-start">
                    <Image src={"https://cdn.brawlify.com/profile-icons/regular/" + player.iconId + ".png"} alt={`Player icon`} width={500} height={500} className="w-50"/>
                    <div>
                        <h1 className="text-3xl sm:text-4xl md:text-5xl lg:text-7xl">{player.name}</h1>
                        <h2 className="text-xl sm:text-xl md:text-2xl lg:text-3xl">{player.trophies} trofeos</h2>
                        <p className="text-lg sm:text-md md:text-lg lg:text-xl">{player.clubName}</p>
                    </div>
                </header>
                <div className="container flex flex-wrap gap-5">
                    <section className="container bg-gray-800 p-10 rounded-4xl justify-items-center lg:justify-start mt-5 flex-1/4">
                        <h2 className="text-2xl sm:text-2xl md:text-3xl text-center">Progreso de temporada</h2>
                        <TrophyChart player={player}/>
                    </section>
                    <section className="container bg-gray-800 p-10 rounded-4xl justify-items-center lg:justify-start mt-5 flex-1/4">
                        <h2 className="text-2xl sm:text-2xl md:text-3xl text-center">Logros</h2>
                        <PlayerAchievementsComponent badges={player.badges} />
                    </section>
                    <section className="container bg-gray-800 p-10 rounded-4xl justify-items-center lg:justify-start mt-5">
                        <h2 className="text-2xl sm:text-2xl md:text-3xl text-center">Registro de megahucha</h2>
                        <MegapigRegistriesComponent player={player} />
                    </section>
                    <section className="container bg-gray-800 p-10 rounded-4xl justify-items-center mt-5">
                        <h2 className="text-2xl sm:text-2xl md:text-3xl text-center">Progreso total</h2>
                        <Line data={{
                            labels: player.trophyRegistries.map(tr => `T${tr.season} S${tr.week}`),
                            datasets: [
                                {
                                    label: 'Trophies',
                                    data: player.trophyRegistries.map(tr => tr.trophies),
                                    borderColor: '#fcc200',
                                    backgroundColor: '#fcc200',
                                    fill: true,
                                    animation: false,
                                    borderWidth: 7,
                                }
                            ],
                        }} options={{
                            responsive: true,
                            scales: {
                                x: {
                                },
                                y: {
                                    min: Math.min(...player.trophyRegistries.map(tr => tr.trophies)),
                                    max: Math.max(...player.trophyRegistries.map(tr => tr.trophies))
                                }
                            },
                            plugins: {
                                legend: {
                                    display: false
                                }
                            }
                        }}/>
                    </section>
                </div>
                
            </div>
        )    
    }
}