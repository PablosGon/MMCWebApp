import { Player } from "@/models/player.model";

class PlayerService {

    private readonly apiUrl = process.env.NEXT_PUBLIC_API_URL

    public async getOrCreatePlayer(playerTag: string): Promise<Player> {

        const player = sessionStorage.getItem(playerTag);

        if (player) {
            const parsedPlayer: Player = JSON.parse(player);
            return parsedPlayer;
        } else {
            try {
                const player = await this.getPlayer(playerTag);
                sessionStorage.setItem(playerTag, JSON.stringify(player))
                return player;
            } catch (error) {
                const err = error as { response: Response };

                if(err.response.status === 404) {
                    return await this.postPlayer(playerTag);
                }
                
                throw error;
            }
        }
    }

    private async getPlayer(playerTag: string): Promise<Player> {
        const response = await fetch(this.apiUrl + `player/${playerTag}`, {
            headers: {
                "isAdmin": sessionStorage.getItem('admin') ?? 'false',
            }
        });

        if (!response.ok) {
            throw new Error(`API Error: ${response.status}`);
        }
        
        return response.json() as Promise<Player>;
    }

    private async  postPlayer(playerTag: string): Promise<Player> {
        const response = await fetch(this.apiUrl + `player`, {
            method: "POST",
            body: JSON.stringify({ playerTag: playerTag }),
            headers: { "Content-Type": "application/json" },
        });

        if (!response.ok) {
            throw new Error(`API Error: ${response.status}`);
        }
        
        return response.json() as Promise<Player>;
    }
}

export const playerService = new PlayerService();