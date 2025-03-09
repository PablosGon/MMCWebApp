import Link from "next/link";

export default function Header() {
    return (
        <header className="h-30 bg-gradient-to-r from-purple-800 to-purple-950 shadow-2xl p-5">
            <Link href="/" className="text-7xl">
                MMC App
            </Link>
            <small className="text-lg">beta</small>
        </header>
    );
}