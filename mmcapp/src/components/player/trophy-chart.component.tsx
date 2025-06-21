import { TrophyChartMode } from "@/enums/trophy-chart-mode.enum";
import { Player } from "@/models/player.model";
import { Chart, LogarithmicScale, CategoryScale, Legend, LinearScale, LineElement, PointElement, Title, Tooltip } from "chart.js";
import { useState } from "react";
import { Line } from "react-chartjs-2";

export function TrophyChart(params:Readonly<{player:Player}>) {
     
    Chart.register(
        CategoryScale,
        LogarithmicScale,
        LinearScale,
        PointElement,
        LineElement,
        Title,
        Tooltip,
        Legend,
    )
        
    const player = params.player;

    const trophyUpgrades = player.seasonTrophyProgress;
    const trophyRegistries = player.trophyRegistries.filter(x => x.week != 0);

    const [mode, setMode] = useState<TrophyChartMode>(TrophyChartMode.Season);

    if(trophyUpgrades.length == 0) {
        return (
            <div className="h-50 flex items-center">
                <p className="text-xl text-center">
                {
                    trophyRegistries.length > 1 ?
                        "¡La temporada acaba de empezar! Vuelve pronto para ver tu progreso de temporada"
                    : 
                        "Aún no tenemos datos sobre este jugador. ¡Vuelve pronto!"
                }
                </p>
            </div>
        )
    }
    return (
        <div className="w-full">
            <form className="max-w-sm w-full flex flex-row border-2 border-gray-600 rounded-2xl m-auto">
                <label className="flex-1 text-center cursor-pointer">
                    <input 
                        type="radio" 
                        name="mode"
                        value={TrophyChartMode.Season} 
                        onChange={() => setMode(TrophyChartMode.Season)} 
                        className="peer hidden"
                    />
                    <span className="block p-2 peer-checked:bg-gray-600 overflow-hidden rounded-l-xl">Temporada</span>
                </label>
                <label className="flex-1 text-center cursor-pointer">
                    <input 
                        type="radio" 
                        name="mode"
                        value={TrophyChartMode.History} 
                        onChange={() => setMode(TrophyChartMode.History)} 
                        className="peer hidden"
                    />
                    <span className="block p-2 peer-checked:bg-gray-600 overflow-hidden rounded-r-2xl">Histórico</span>
                </label>
            </form>

            {
                mode === TrophyChartMode.History ?
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
                :
                    <Line data={{
                        labels: [0, ...trophyUpgrades].map((tr, week) => week == 0 ? 'Inicio' : `Semana ${week}`),
                        datasets: [
                            {
                                label: 'Trophies',
                                data: [0, ...trophyUpgrades],
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
                                min: 0,
                                max: Math.max(...trophyUpgrades, 2000)
                            }
                        },
                        plugins: {
                            legend: {
                                display: false
                            }
                        }
                    }}/>
            }
            
        </div>
    )
}