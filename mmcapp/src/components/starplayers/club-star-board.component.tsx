import { CLUBS } from "@/constants/clubs-names.constant";
import { ClubStarPlayerRegistry } from "@/models/club-star-player-registry.model";
import Image from "next/image";
import Link from "next/link";

export function ClubStarBoardComponent(params: Readonly<{club: ClubStarPlayerRegistry, index: number}>) {

    const club = params.club;
    const index = params.index;

    return (
        <div>
            <h2 className="text-3xl mb-5">
                { CLUBS[index].name }
            </h2>
            {
                club.weeklyStarPlayers.length > 0 ?
                    <section className="flex flex-col gap-5">
                        <div className="flex flex-col md:flex-row gap-5 justify-center">
                            {
                                club.grandStarPlayer &&
                                    <article className="flex flex-col justify-center items-center">
                                        <h3 className="text-2xl">
                                            Gran Estelar
                                        </h3>
                                        <Link href={`club/0/player/${club.grandStarPlayer.playerTag}`}>
                                            <Image src={club.grandStarPlayer.profileIconId != -1 ? "https://cdn.brawlify.com/profile-icons/regular/" + club.grandStarPlayer.profileIconId + ".png" : "/profile-icon-default.png"} height={200} width={200} alt={club.grandStarPlayer.name}/>
                                        </Link>
                                        <p className="text-xl">
                                            {club.grandStarPlayer.name}
                                        </p>
                                        <p className="text-lg text-amber-400">
                                            +{club.grandStarPlayer.trophies} trofeos
                                        </p> 
                                    </article>
                            }
                            {
                                club.starLegend &&
                                    <article className="flex flex-col justify-center items-center">
                                        <h3 className="text-2xl">
                                            Leyenda Estelar
                                        </h3>
                                        <Link href={`club/0/player/${club.starLegend.playerTag}`}>
                                            <Image src={club.starLegend.profileIconId != -1 ? "https://cdn.brawlify.com/profile-icons/regular/" + club.starLegend.profileIconId + ".png" : "/profile-icon-default.png"} height={200} width={200} alt={club.starLegend.name}/>
                                        </Link>
                                        <p className="text-xl">
                                            {club.starLegend.name}
                                        </p>
                                        <p className="text-lg text-emerald-400">
                                            {club.starLegend.trophies} trofeos totales
                                        </p> 
                                    </article>
                                    
                            }
                            {
                                club.starMaster &&
                                    <article className="flex flex-col justify-center items-center">
                                        <h3 className="text-2xl">
                                            Maestro Estelar
                                        </h3>
                                        <Link href={`club/0/player/${club.starMaster.playerTag}`}>
                                           <Image src={club.starMaster.profileIconId != -1 ? "https://cdn.brawlify.com/profile-icons/regular/" + club.starMaster.profileIconId + ".png" : "/profile-icon-default.png"} height={200} width={200} alt={club.starMaster != null ? club.starMaster.name : 'XD'}/>
                                        </Link>
                                        <p className="text-xl">
                                            {club.starMaster.name}
                                        </p>
                                        <p className="text-lg text-blue-300">
                                            {club.starMaster.rankedPoints} puntos
                                        </p> 
                                    </article>
                            }
                        </div>
                        <article className="flex flex-col justify-center items-center">
                            <h3 className="text-2xl">
                                Estelares semanales
                            </h3>
                                <ul className="flex flex-1/2 justify-center flex-wrap gap-5">
                                {
                                    club.weeklyStarPlayers.map((starPlayer) => (
                                        <li key={starPlayer.week} className="flex flex-col justify-center items-center">
                                            <p className="text-md">
                                                Semana {starPlayer.week}
                                            </p>
                                            <Link href={`club/0/player/${starPlayer.playerTag}`}>
                                                <Image src={starPlayer.profileIconId != -1 ? "https://cdn.brawlify.com/profile-icons/regular/" + starPlayer.profileIconId + ".png" : "/profile-icon-default.png"} height={100} width={100} alt={starPlayer.name}/>
                                            </Link>
                                            <p className="text-lg">
                                                {starPlayer.name}
                                            </p>
                                            <p className="text-md text-amber-400">
                                                +{starPlayer.trophies} trophies
                                            </p> 
                                        </li>
                                    ))
                                }
                            </ul>

                        </article>
                    </section>
                    :
                    <p>Sin datos</p>
            }
            
        </div>
    )
}