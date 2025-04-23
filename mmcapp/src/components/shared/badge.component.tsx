import Image from "next/image";

export function BadgeComponent(params: { image: string, title: string, subtitle: string, subtitleColor: string | undefined, size: number | undefined }) {

    const {image, title, subtitle, subtitleColor, size} = params;

    return (
        <div className="flex flex-col items-center">
            <Image src={image} height={size ? size : 200} width={size ? size : 200} alt="Gran estelar"/>
            <p className="text-xl">
                {title}
            </p>
            <p className={"text-lg" + subtitleColor ? subtitleColor : ""}>
                {subtitle}
            </p>
        </div>
    )

}