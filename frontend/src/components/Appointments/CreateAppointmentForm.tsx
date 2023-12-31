import { DatePicker, TimeRangeInput } from "@mantine/dates";
import React, { useState } from "react";
import { API_ROOT, carModels } from "src/utilities/constants";
import { useVehicleOwner } from "src/utilities/hooks/useVehicleOwner";
import { APIError, Appointment, Service } from "src/utilities/interfaces";
import {MuiTelInput} from "mui-tel-input";

interface FormData {
    firstName: string,
    lastName: string,
    email: string,
    phoneNumber: string,
    vehicleMake: string,
    vehicleModel: string,
    vehicleYear: string,
    vehicleVIN: string,
    liscensePlate: string,
    serviceType: string,
    notes: string,
}

//list of all car models

interface AppointmentFormProps {
    shopId: string,
    setVisibility: React.Dispatch<React.SetStateAction<boolean>>,
    services: Service[]
}

const CreateAppointmentForm = ({setVisibility, services, shopId}: AppointmentFormProps) => {
    const initialForm: FormData = {
        firstName: "",
        lastName: "",
        email: "",
        phoneNumber: "",
        vehicleMake: "Acura",
        vehicleModel: "ILX",
        vehicleYear: "2022",
        vehicleVIN: "",
        liscensePlate: "",
        serviceType: "",
        notes: "",
    }
    const [formData, setFormData] = useState<FormData>(initialForm);
    const [timeRange, setTimeRange] = useState<[Date, Date]>([new Date(), new Date()]);
    const [day, setDay] = useState<Date | null>(new Date());
    const [error, setError] = useState("");
    const {vehicleOwner, setVehicleOwner} = useVehicleOwner();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const changing = e.target.name;
        const newFormData: FormData = {...formData, [changing]: e.target.value};
        setFormData(newFormData);
    }
    console.log(timeRange);
    const CarMakeDropdown = () => {
        // create drop down with all car makes
        return (
            <div>
                <select value={formData.vehicleMake} onChange={e => setFormData({
                    ...formData,
                    vehicleMake: e.target.value,
                    vehicleModel: "Choose a Model"
                })} className='shadow-sm border border-gray-300 rounded text-gray-700 leading-tight
            focus:outline-blue-500 focus:shadow-outline hover:border-gray-700 py-1'>
                    {Object.keys(carModels).map((make) => {
                        return (
                            <option value={make}>{make}</option>
                        )
                    })}
                </select>
            </div>
        )
    }

    interface YearDropDownProps {
        start: number,
        currentYear: number
    }

    const YearDropDown = ({start, currentYear}: YearDropDownProps) => {
        let options: JSX.Element[] = [];
        for (let i = start; i <= currentYear; i++) {
            options.push(<option key={i} value={i}>{i}</option>)
        }

        return (
            <select value={formData.vehicleYear} onChange={e => setFormData({...formData, vehicleYear: e.target.value})}
                    className='shadow-sm border border-gray-300 rounded text-gray-700 leading-tight
            focus:outline-blue-500 focus:shadow-outline hover:border-gray-700 py-1'>
                {options}
            </select>
        )
    }
    const ServiceTypeDropDown = () => {
        // change to display services with fixed costs
        const options: JSX.Element[] = services.map(service => {
            if (service.name !== "Other" && service.defaultPrice !== null) {
                return (
                    <option value={service.name}>{service.name}</option>
                )
            }

            // return no option when the service is other, this form is for directly booking an appointment
            return <></>;
        })
        options.push(<option value={""}></option>)
        return (
            <select value={formData.serviceType} onChange={e => setFormData({...formData, serviceType: e.target.value})}
                    className='shadow-sm border border-gray-300 rounded text-gray-700 leading-tight
            focus:outline-blue-500 focus:shadow-outline hover:border-gray-700 py-1'>
                {options}
            </select>
        )
    }
    const CarModelDropdown = () => {
        // create drop down with all car models
        const models: string[] = carModels[formData.vehicleMake];
        const modelOptions = models.map(model => {
            return <option value={model}>{model}</option>
        })
        return (
            <div>
                <select value={formData.vehicleModel}
                        onChange={e => setFormData({...formData, vehicleModel: e.target.value})} className='shadow-sm border border-gray-300 rounded text-gray-700 leading-tight
            focus:outline-blue-500 focus:shadow-outline hover:border-gray-700 py-1'>
                    {modelOptions}
                </select>
            </div>
        );
    }
    const handleAppointmentSubmit = async () => {
        // handle case where vehicle owner id exists in local storage
        const servicePrice = services.find(service => service.name === formData.serviceType)?.defaultPrice
        if (servicePrice === null) return;
        let reqBody: any = {
            shopId: shopId,
            startTime: timeRange[0].toISOString(),
            endTime: timeRange[1].toISOString(),
            serviceName: formData.serviceType,
            price: servicePrice
        }
        if (vehicleOwner && day && timeRange) {

            const res = await fetch(`${API_ROOT}/vehicleOwner/${vehicleOwner}/appointments`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(reqBody)
            })
            if (res.ok) {
                console.log("Appointment successfully createed");
                setVisibility(false);
                return;
            }
            console.log("Appointment COULD NOT be created");
            const error: APIError = await res.json();
            console.log(error.message);
            setError(error.message);
        } else {
            const vOwner = {
                firstName: formData.firstName,
                lastName: formData.lastName,
                email: formData.email,
                phoneNumber: formData.phoneNumber,
                vehicle: {
                    year: Number(formData.vehicleYear),
                    make: formData.vehicleMake,
                    model: formData.vehicleModel,
                    vin: formData.vehicleVIN,
                    plate: formData.liscensePlate
                }
            }
            reqBody = {...reqBody, vehicleOwner: vOwner}
            const res = await fetch(`${API_ROOT}/appointments`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(reqBody)
            })
            if (res.ok) {
                console.log("Appointment successfully createed");
                const data: Appointment = await res.json();
                setVisibility(false);
                setVehicleOwner(String(data.vehicleOwner.id));
                return;
            }
            console.log("Appointment COULD NOT be created");
            const error: APIError = await res.json();
            console.log(error.message);
            setError(error.message);
        }
        return;
    }

    return (
        <div className="flex justify-center mb-8">
            <form className="grid grid-cols-1 gap-1 w-[400px]">
                <h3 className="text-2xl pt-2 text-blue-800 sm:text-3xl">Create An Appointment</h3>
                <div className={vehicleOwner ? "hidden" : ""}>
                    <section className="flex">
                        <div className="pr-1">
                            <label>First Name</label>
                            <input
                                name="firstName"
                                className="shadow-sm appearance-none border border-gray-300 rounded w-full py-2 px-3 text-gray-700 leading-tight
                                focus:outline-blue-500 focus:shadow-outline hover:border-gray-700"
                                type="text"
                                value={formData.firstName}
                                onChange={handleChange}
                            />
                        </div>
                        <div className="pl-1">
                            <label>Last Name</label>
                            <input
                                className="shadow-sm appearance-none border border-gray-300 rounded w-full py-2 px-3 text-gray-700 leading-tight
                                focus:outline-blue-500 focus:shadow-outline hover:border-gray-700"
                                name="lastName"
                                type="text"
                                value={formData.lastName}
                                onChange={handleChange}
                            />
                        </div>
                    </section>
                    <label>Email: </label>
                    <div>
                        <input
                            className="shadow-sm appearance-none border border-gray-300 rounded w-full py-2 px-3 text-gray-700 leading-tight
                            focus:outline-blue-500 focus:shadow-outline hover:border-gray-700"
                            type="text"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                        />
                    </div>
                    <label className='mt-1 font-semibold'>
                        Phone Number
                    </label>
                    <div className={"mb-4"}>
                        <MuiTelInput className='col-span-2 shadow-sm' fullWidth value={formData.phoneNumber}
                                     onChange={(phoneNumber) => setFormData({...formData, phoneNumber: phoneNumber})}
                                     onlyCountries={['CA', 'US']} focusOnSelectCountry defaultCountry='CA'/>
                    </div>
                    <section className="flex justify-between">
                        <div>
                            <label>Vehicle Make: </label>
                            <CarMakeDropdown/>
                        </div>
                        <div>
                            <label>Vehicle Model: </label>
                            <CarModelDropdown/>
                        </div>
                    </section>
                    <label>Vehicle Year: </label>
                    <div>
                        {/* {YearDropDown(1990, 2022)} */}
                        <YearDropDown start={1990} currentYear={2022}/>
                    </div>
                    <label>Vehicle VIN: </label>
                    <div>
                        <input
                            className="shadow-sm appearance-none border border-gray-300 rounded w-full py-2 px-3 text-gray-700 leading-tight
                            focus:outline-blue-500 focus:shadow-outline hover:border-gray-700"
                            type="text"
                            name="vehicleVIN"
                            value={formData.vehicleVIN}
                            onChange={handleChange}
                        />
                    </div>
                    <label>License Plate: </label>
                    <div>
                        <input
                            className="shadow-sm appearance-none border border-gray-300 rounded w-full py-2 px-3 text-gray-700 leading-tight
                            focus:outline-blue-500 focus:shadow-outline hover:border-gray-700"
                            type="text"
                            name="liscensePlate"
                            value={formData.liscensePlate}
                            onChange={handleChange}
                        />
                    </div>
                </div>
                <label className="">Service Type</label>
                <div className="">
                    <ServiceTypeDropDown/>
                </div>

                {/* Choose date and time of appointment */}
                <div className="text-xl text-blue-800 sm:text-2xl mt-3 mb-1">Choose a time that works</div>
                <div>
                    <DatePicker label="Appointment Date" excludeDate={date => date < new Date()} value={day}
                                onChange={date => setDay(date)}/>
                    <TimeRangeInput format="12" label="Appointment Time" value={timeRange} onChange={setTimeRange}/>
                </div>
                <label className="align-top">Additional Notes: </label>
                <div>
                    <textarea
                        className="shadow-sm appearance-none border border-gray-300 rounded w-full py-2 px-3 text-gray-700 leading-tight
                        focus:outline-blue-500 focus:shadow-outline hover:border-gray-700 h-48 text-start p-1"
                        value={formData.notes}
                        onChange={(e) => setFormData({...formData, notes: e.target.value})}
                        datatype="text"
                    />
                </div>
                {error && <div className="text-red-500 text-xs">{error}</div>}
                <div className="flex justify-between">
                    <button
                        onClick={() => setVisibility(false)}
                        className="transition duration-100 ease-in-out w-32 bg-white hover:bg-gray-100
                        text-gray-600 font-semibold py-2 px-4 rounded border border-gray-400">Cancel
                    </button>
                    <button
                        onClick={e => {
                            e.preventDefault()
                            handleAppointmentSubmit()
                        }}
                        className="transition duration-100 ease-in-out w-[125px] bg-blue-500 hover:bg-blue-700 text-white
                        font-semibold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Submit
                    </button>
                </div>
            </form>
        </div>
    )
}
export default CreateAppointmentForm;
