import { Moment } from 'moment';

export interface ISprawa {
    id?: number;
    stan?: string;
    kwota?: string;
    klient?: string;
    adres?: string;
    telefon?: string;
    status?: string;
    numer?: string;
    utworzona?: Moment;
    data?: Moment;
}

export class Sprawa implements ISprawa {
    constructor(
        public id?: number,
        public stan?: string,
        public kwota?: string,
        public klient?: string,
        public adres?: string,
        public telefon?: string,
        public status?: string,
        public numer?: string,
        public utworzona?: Moment,
        public data?: Moment
    ) {}
}
