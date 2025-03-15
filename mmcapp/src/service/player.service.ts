import { Player } from "@/models/player.model";

export async function getPlayer(playerTag: string): Promise<Player> {
    const response = await fetch(process.env.NEXT_PUBLIC_API_URL + `player/${playerTag}`);

    if (!response.ok) {
        throw new Error(`API Error: ${response.status}`);
    }
    
    return response.json() as Promise<Player>;
}