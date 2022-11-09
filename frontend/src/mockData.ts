import { ShopOwner, Shop, Address, Appointment, VehicleOwner, Quote } from "./interfaces";
export const mVehicleOwner: VehicleOwner = {
    id: 2,
    firstName: "Hamoon",
    lastName: "Zamiri",
    userName: "hamoon",
    phoneNumber: "747-223-1190",
    email: "hz@gmail.com",
    vehicle: {
        year: 2002,
        make: "Toyota",
        model: "Camry",
        vin: "2313123",
        plate: "bxmt 293"
    }
}
export const mAppointment: Appointment = {
    appointmentId: 1,
    vehicleOwner: mVehicleOwner,
    startDate: new Date(),
    endDate: new Date(),
    duration: 100
}
export const mQuote: Quote = {
    vehicleOwner: mVehicleOwner,
    price: 120.00,
    expiryTime: "2020-12-12"
}
const generateAppointments = () => {
    let appointments: Appointment[] = [];
    for(let i = 0; i < 20; i++){
        appointments.push(mAppointment);
    }
    return appointments;
}
const genereateQuotes = () => {
    let quotes: Quote[] = [];
    for(let i = 0; i < 20; i++){
        quotes.push(mQuote);
    }
    return quotes;
}
export const mAddress: Address = {
    addressId: 1,
    street: "Daemon Blvd",
    streetNumber: "123",
    postalCode: "M4B192",
    province: "ON"
}
export const mShop: Shop = {
    shopId: 1,
    shopName: "Uths Garage",
    address: mAddress,
    appointments: generateAppointments(),
    quotes: genereateQuotes()
}
export const mShopOwner: ShopOwner = {
    id: 1,
    firstName: "Uthman",
    lastName: "Mohammed",
    userName: "123Uth",
    phoneNumber: "416-445-9898",
    email: "123uth@gmail.com",
    shop: mShop
}
