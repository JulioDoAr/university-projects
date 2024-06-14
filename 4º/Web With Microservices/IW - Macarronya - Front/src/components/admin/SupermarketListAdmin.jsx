import { SupermarketCardAdmin } from "./SupermarketCardAdmin"
export function SupermarketListAdmin({supermarkets}){

    return (
        <div className="supermarket-list">
            
                {supermarkets.map((s, index) => (<SupermarketCardAdmin key={index} supermarket={s} />))}
        </div>
    )
}