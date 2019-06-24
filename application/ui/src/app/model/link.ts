export interface Affordance {
    title: string|null;
    method: 'get'|'post'|'put'|'delete'|'patch';
    contentType: string;
    properties: { name: string, required: boolean }[];
}

export interface Link {
    href: string;
}

export interface ResourceLinks {
    self: Link;
    [key: string]: Link;
}

