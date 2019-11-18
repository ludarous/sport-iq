import * as FileSaver from 'file-saver';

export class BlobUtils {
  static openBlob(blob: Blob, inNewWindow: boolean = false) {

    const target = inNewWindow ? '_blank' : '_self';

    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
      window.navigator.msSaveOrOpenBlob(blob);
    } else {
      const fileURL = BlobUtils.createBlobURL(blob);
      window.open(fileURL, target);
    }
  }

  static saveBlob(blob: Blob, fileName?: string) {
    const name = fileName ? fileName : 'download';
    FileSaver.saveAs(blob, name);
  }

  static createBlobURL(blob: Blob): string {
    return (window.URL) ? window.URL.createObjectURL(blob) : (<any>window).webkitURL.createObjectURL(blob);
  }
}
