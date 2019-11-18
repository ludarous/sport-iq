import {Subject} from 'rxjs';
import {Injectable} from '@angular/core';

export class FileProgress {
  file: File;
  progress: number;

  constructor(file: File, progress: number) {
    this.file = file;
    this.progress = progress;
  }
}

@Injectable()
export class FileUploadService<T> {

  private progressMap: Map<File, number> = new Map();

  public onBeforeUploadSource = new Subject<File>();
  public onBeforeUpload$ = this.onBeforeUploadSource.asObservable();

  public onProgressSource = new Subject<FileProgress>();
  public onProgress$ = this.onProgressSource.asObservable();

  public onUploadSource = new Subject<T>();
  public onUpload$ = this.onUploadSource.asObservable();

  public onErrorSource = new Subject<File>();
  public onError$ = this.onErrorSource.asObservable();

  public uploadFiles(files: File[], url: string, entityId: number, description: string, csrf: string = null, token: string = null) {
    for (const file of files) {

      this.progressMap.set(file, 0);

      const xhr = new XMLHttpRequest();
      xhr.withCredentials = true;
      const formData = new FormData();

      this.onBeforeUploadSource.next(file);

      formData.append('file', file, file.name);

      xhr.upload.addEventListener('progress', (e: ProgressEvent) => {
        if (e.lengthComputable) {
          const progress = Math.round((e.loaded * 100) / e.total);
          this.progressMap.set(file, progress);
          this.onProgressSource.next(new FileProgress(file, progress));
        }
      }, false);

      xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
          this.progressMap.set(file, 0);

          if (xhr.status >= 200 && xhr.status < 300) {
            const response = <T>JSON.parse(xhr.response); // TODO: Handle possible cast error
            this.onUploadSource.next(response);
          } else {
            this.onErrorSource.next(file);
          }
        }
      };


      if (description) formData.append('description', description);
      if (entityId) formData.append('entityId', entityId.toString());
      if (csrf) formData.append('_csrf', csrf);

      xhr.open('POST', url, true);
      if (token) xhr.setRequestHeader('Authorization', 'Bearer ' + token);
      if (csrf) xhr.setRequestHeader('X-XSRF-TOKEN', csrf);


      xhr.send(formData);
    }
  }
}
