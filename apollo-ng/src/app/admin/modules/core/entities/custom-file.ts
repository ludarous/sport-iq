export interface ICustomFile {
  id?: number;
  name?: string;

  description?: string;
  size?: number;
  contentContentType?: string;
}

export class CustomFile implements ICustomFile {
  id: number;
  name: string;

  description: string;
  size: number;
  contentContentType: string;
}
