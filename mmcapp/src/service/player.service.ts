import { Player } from "@/models/player.model";

export async function getOrCreatePlayer(playerTag: string) {
    try {
        const player = await getPlayer(playerTag);
        return player;
    } catch (error) {
        const err = error as { response: Response };
        if(err.response.status === 404) {
            console.log(`Player ${playerTag} not found. Creating...`);
            return await postPlayer(playerTag); // Si es un 404, lo creamos
        }
        throw error;
    }
}

async function getPlayer(playerTag: string): Promise<Player> {
    const response = await fetch(process.env.NEXT_PUBLIC_API_URL + `player/${playerTag}`);

    if (!response.ok) {
        throw { response };
    }
    
    return response.json() as Promise<Player>;
}

async function postPlayer(playerTag: string): Promise<Player> {
    const response = await fetch(process.env.NEXT_PUBLIC_API_URL + `player`, {
        method: "POST",
        body: JSON.stringify({ playerTag: playerTag }),
        headers: { "Content-Type": "application/json" },
    });

    if (!response.ok) {
        throw { response };
    }
    
    return response.json() as Promise<Player>;
}