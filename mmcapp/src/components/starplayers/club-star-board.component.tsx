import { CLUBS } from "@/constants/clubs-names.constant";
import { ClubStarPlayerRegistry } from "@/models/club-star-player-registry.model";
import Image from "next/image";

export function ClubStarBoardComponent(params: {club: ClubStarPlayerRegistry, index: number}) {

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
                            <article className="flex flex-col justify-center items-center">
                                <h3 className="text-2xl">
                                    Gran Estelar
                                </h3>
                                <Image src={"https://cdn.brawlify.com/profile-icons/regular/" + (club.grandStarPlayer != null ? club.grandStarPlayer.profileIconId : "XD") + ".png"} height={200} width={200} alt={club.grandStarPlayer != null ? club.grandStarPlayer.name : 'XD'}/>
                                <p className="text-xl">
                                    {club.grandStarPlayer != null ? club.grandStarPlayer.name : 'XD'}
                                </p>
                                <p className="text-lg text-amber-400">
                                    +{club.grandStarPlayer != null ? club.grandStarPlayer.trophies : 'XD'} trofeos
                                </p> 
                            </article>
                            <article className="flex flex-col justify-center items-center">
                                <h3 className="text-2xl">
                                    Leyenda Estelar
                                </h3>
                                <Image src={"https://cdn.brawlify.com/profile-icons/regular/" + (club.starLegend != null ? club.starLegend.profileIconId : "XD") + ".png"} height={200} width={200} alt={club.grandStarPlayer != null ? club.grandStarPlayer.name : 'XD'}/>
                                <p className="text-xl">
                                    {club.starLegend != null ? club.starLegend.name : 'XD'}
                                </p>
                                <p className="text-lg text-emerald-400">
                                    {club.starLegend != null ? club.starLegend.trophies : 'XD'} trofeos totales
                                </p> 
                            </article>
                            <article className="flex flex-col justify-center items-center">
                                <h3 className="text-2xl">
                                    Maestro Estelar
                                </h3>
                                <Image src={"https://cdn.brawlify.com/profile-icons/regular/" + (club.starMaster != null ? club.starMaster.profileIconId : "XD") + ".png"} height={200} width={200} alt={club.grandStarPlayer != null ? club.grandStarPlayer.name : 'XD'}/>
                                <p className="text-xl">
                                    {club.starMaster != null ? club.starMaster.name : 'XD'}
                                </p>
                                <p className="text-lg text-blue-300">
                                    {club.starMaster != null ? club.starMaster.rankedPoints : 'XD'} puntos
                                </p> 
                            </article>
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
                                            <Image src={"https://cdn.brawlify.com/profile-icons/regular/" + starPlayer.profileIconId + ".png"} height={100} width={100} alt={club.grandStarPlayer != null ? club.grandStarPlayer.name : 'XD'} className="min-w-10"/>
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