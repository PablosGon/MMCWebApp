import { Player } from "@/models/player.model";
import { Chart, LogarithmicScale, CategoryScale, Legend, LinearScale, LineElement, PointElement, Title, Tooltip } from "chart.js";
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

    console.log("Trophy Registries:", player.trophyRegistries.filter(x => x.week != 0))

    console.log([0, ...trophyUpgrades], trophyUpgrades)

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
        <div className={`max-w-100`}>
            <Line data={{
                labels: [0, ...trophyUpgrades],
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
                        display: false
                    },
                    y: {
                        display: false,
                        min: -200,
                        max: Math.max(...trophyUpgrades, 1200 + 200)
                    }
                },
                plugins: {
                    legend: {
                        display: false
                    }
                }
            }}/>
            <div className="flex">
                <div className="flex-1 justify-items-center">
                    <p className="text-md sm:text-lg md:text-xl">Inicio</p>
                </div>
                {
                    trophyUpgrades.map((tu, index) => (
                        <div key={"tu"+index} className="flex-1 justify-items-center">
                            <p className="text-md sm:text-lg md:text-xl">Sem {index+1}</p>
                            <p className="text-amber-400">+{tu}</p>
                        </div>
                    ))
                }
            </div>
        </div>
    )
}