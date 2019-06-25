export interface DomainConfig<T> {
    properties: PropertyConfig[];
    rel: string;
    model: T;
    validate?: (value: T ) => boolean;
}

export interface PropertyConfig {
    name: string;
    isId?: boolean;
    options?: {
        required: boolean;
        inputType: InputType
        autoComplete?: boolean,
        referenceListRel?: string
    }|undefined;
}

export enum InputType {
'color',
'date',
'datetime-local',
'email',
'month',
'number',
'password',
'search',
'tel',
'text',
'time',
'url',
'week'
}
