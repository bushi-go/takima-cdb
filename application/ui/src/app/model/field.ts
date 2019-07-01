export interface Field {
    key: string;
    label: string;
    controlType: FieldType;
    type: InputType;
    value: any;
    required: boolean;
    refList?: any[];
    refRel?: string;
}

export enum FieldType{
    'input'=1,
    'select'=2,
    'radioButton'=3,
    'radioGroup'=4,
    'checkbox'=5,
    'hidden'=6,
    //'autocomplete',
    'datePicker'=7,
    // TODO : add HammerJS support for these
    //'slider',
    //'slide-toggle'
}

export enum InputType {
    
    'color'=1,
    'date'=2,
    'datetime-local'=3,
    'email'=4,
    'month'=5,
    'number'=6,
    'password'=7,
    'search'=8,
    'tel'=9,
    'text'=10,
    'time'=11,
    'url'=12,
    'week'=13
}
