"use client"
import ErrorComponent from "@/components/shared/error.component";
import LoadingComponent from "@/components/shared/loading.component";
import { Season } from "@/models/season.model";
import { getSeasons } from "@/service/season.service";
import { useEffect, useState } from "react";
import { SeasonStarBoardComponent } from "@/components/starplayers/season-star-board.component";
import Link from "next/link";

export default function StarPlayersPage() {

    const [seasons, setSeasons] = useState<Season[] | undefined>(undefined);
    const [error, setError] = useState<boolean>(false);

    useEffect(() => {
        const fetchSeasons = async () => {
            try {
                const data = await getSeasons();
                setSeasons(data);    
            } catch (error) {
                console.log(error);
                setError(true);
            }
        }

        fetchSeasons();
    }, []);

    if (error) {
        return (
            <ErrorComponent/>
        );
    } else if (!seasons) {
        return (
            <LoadingComponent/>
        );
    } else {
        return (
            <div className="max-w-250 m-auto">
                <Link href="/" className="text-lg">← Volver</Link>
                <ul className="flex flex-col gap-5">
                    {
                        seasons.map((season) => (
                            seasonIsNotNew(season) &&
                                <li key={season.id} className="bg-gray-800 p-5 rounded-2xl">
                                    <SeasonStarBoardComponent season={season} />
                                </li>
                        ))
                    }
                </ul>
            </div>

        );
    }
}

function seasonIsNotNew(season:Season) {
    let isNew = true;
    season.starPlayersByClub.forEach((club) => {
        console.log(club.weeklyStarPlayers);
        if(club.weeklyStarPlayers.length > 0) {
            isNew = false;
        }
    });
    return !isNew;
}