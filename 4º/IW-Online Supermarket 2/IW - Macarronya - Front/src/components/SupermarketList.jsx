import React from "react";
import SupermarketCard from "./SupermarketCard";

export default function SupermarketList({ supermarkets }) {
    return (
        <div className="supermarket-list">
            
                {supermarkets.map((s, index) => (<SupermarketCard key={index} supermarket={s} />))}
                    </div>
    );
}