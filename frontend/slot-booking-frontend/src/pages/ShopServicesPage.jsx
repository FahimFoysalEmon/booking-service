import { useParams } from "react-router-dom"


export default function ShopServicesPage() {

    const { shopId } = useParams();

    return (

        <div>
            <h1>Services</h1>
            <p>Shop ID: {shopId}</p>
        </div>

    )
}