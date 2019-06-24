export interface Affordance {
    title: string|null;
    method: 'get'|'post'|'put'|'delete'|'patch';
    contentType: string;
    properties: { [key: string]: any }[];
}

export interface Link {
    href: string;
}

export interface ResourceLinks{
    self: Link;
    [key: string]: Link;
}
