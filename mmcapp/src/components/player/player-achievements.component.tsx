import { StarBadgeCase } from "@/models/star-badge-case.model";
import Image from "next/image";
import { useState } from "react";

export function PlayerAchievementsComponent(params: { badges: StarBadgeCase }) {

    const badges = params.badges;

    const [openIndex, setOpenIndex] = useState<number>(0);

    const toggle = (index: number) => {
        setOpenIndex(openIndex === index ? 0 : index)
    }

    return (
        <div className="flex flex-col w-full">
            <article>
                <header className="flex flex-row items-center" onClick={() => toggle(1)}>
                    <Image src="/star_players/grand_star_player.png" width={50} height={50} alt="Gran estelar" />
                    <p>Gran estelar</p>
                    <p className="ml-auto">{badges.grandStarPlayerBadges.length}</p>
                </header>
                {
                    openIndex === 1 &&
                        <div>
                            <p className="text-sm">El título de Gran Estelar se otorga al jugador que más trofeos ha subido a lo largo de una temporada.</p>
                            <ul className="flex flex-row flex-wrap justify-center flex-1">
                                {
                                    badges.grandStarPlayerBadges.map((badge, index) => (
                                        <li key={index} className="flex flex-col items-center flex-1">
                                            <Image src="/star_players/grand_star_player.png" height={100} width={100} alt="Gran estelar"/>
                                            <p className="text-xl">
                                                Temporada {badge.seasonId}
                                            </p>
                                            <p className="text-lg text-amber-400">
                                                +{badge.trophies} trofeos
                                            </p> 
                                        </li>
                                    ))
                                }
                            </ul>
                        </div>
                }
            </article>
            <hr/>
            <article>
                <header className="flex flex-row items-center" onClick={() => toggle(2)}>
                    <Image src="/star_players/star_legend.png" width={50} height={50} alt="Leyenda estelar" />
                    <p>Leyenda estelar</p>
                    <p className="ml-auto">{badges.starLegendBadges.length}</p>
                </header>
                {
                    openIndex === 2 &&
                        <div>
                            <p className="text-sm">El título de Leyenda Estelar se otorga al jugador que más trofeos totales tenga acumulados al final de la temporada.</p>
                            <ul className="flex flex-row flex-wrap justify-center flex-1">
                                {
                                    badges.starLegendBadges.map((badge, index) => (
                                        <li key={index} className="flex flex-col items-center flex-1">
                                            <Image src="/star_players/star_legend.png" height={100} width={100} alt="Gran estelar"/>
                                            <p className="text-md">
                                                Temporada {badge.seasonId}
                                            </p>
                                            <p className="text-sm text-emerald-400 text-center">
                                                {badge.trophies} trofeos totales
                                            </p> 
                                        </li>
                                    ))
                                }
                            </ul>
                        </div>
                }
            </article>
            <hr/>
            <article>
                <header className="flex flex-row items-center" onClick={() => toggle(3)}>
                    <Image src="/star_players/star_master.png" width={50} height={50} alt="Maestro estelar" />
                    <p>Maestro estelar</p>
                    <p className="ml-auto">{badges.starMasterBadges.length}</p>
                </header>
                {
                    openIndex === 3 &&
                        <div>
                            <p className="text-sm">El título de Maestro Estelar se otorga al jugador que más alto se encuentre en ranked al final de la temporada.</p>
                            <ul className="flex flex-row flex-wrap justify-center flex-1">
                                {
                                    badges.starMasterBadges.map((badge, index) => (
                                        <li key={index} className="flex flex-col items-center flex-1">
                                            <Image src="/star_players/star_master.png" height={100} width={100} alt="Gran estelar"/>
                                            <p className="text-md">
                                                Temporada {badge.seasonId}
                                            </p>
                                            <p className="text-sm text-blue-300 text-center">
                                                {badge.rankedPoints} puntos
                                            </p> 
                                        </li>
                                    ))
                                }
                            </ul>
                        </div>
                }
            </article>
            <hr/>
            <article>
                <header className="flex flex-row items-center" onClick={() => toggle(4)}>
                    <Image src="/star_players/star_player.png" width={50} height={50} alt="Estelar" />
                    <p>Jugador estelar</p>
                    <p className="ml-auto">{badges.starPlayerBadges.length}</p>
                </header>
                {
                    openIndex === 4 &&
                        <div>
                            <p className="text-sm">El título de Jugador Estelar se otorga al jugador que más trofeos ha subido a lo largo de una semana.</p>
                            <ul className="flex flex-row flex-wrap justify-center flex-1 gap-2">
                                {
                                    badges.starPlayerBadges.map((badge, index) => (
                                        <li key={index} className="flex flex-col items-center flex-1">
                                            <Image src="/star_players/star_player.png" height={100} width={100} alt="Gran estelar"/>
                                            <p className="text-md">
                                                Temporada {badge.seasonId}
                                            </p>
                                            <p className="text-md">
                                                Semana {badge.week}
                                            </p>
                                            <p className="text-sm text-amber-400 text-center">
                                                +{badge.trophies} trofeos
                                            </p> 
                                        </li>
                                    ))
                                }
                            </ul>
                        </div>
                }
            </article>
        </div>
    )

}