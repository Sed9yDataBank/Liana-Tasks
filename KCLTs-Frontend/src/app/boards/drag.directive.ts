import { Directive, HostListener, HostBinding, EventEmitter, Output } from '@angular/core';
import { SafeUrl, DomSanitizer } from '@angular/platform-browser';

export interface FileHandle {
  file: File,
  url: SafeUrl
}

@Directive({
  selector: '[appDrag]'
})
export class DragDirective {
 
  @Output() files: EventEmitter<FileHandle[]> = new EventEmitter();

  @HostBinding("style.color") private color = "green";

  constructor(private sanitizer: DomSanitizer) { }

  @HostListener("dragover", ["$event"]) public onDragOver(evt: DragEvent) {
    evt.preventDefault();
    evt.stopPropagation();
    this.color = "green";
  }

  @HostListener("dragleave", ["$event"]) public onDragLeave(evt: DragEvent) {
    evt.preventDefault();
    evt.stopPropagation();
    this.color = "green";
  }

  @HostListener('drop', ['$event']) public onDrop(evt: DragEvent) {
    evt.preventDefault();
    evt.stopPropagation();
    this.color = 'white';
  
    let files: FileHandle[] = [];
    for (let i = 0; i < evt.dataTransfer.files.length; i++) {
      const file = evt.dataTransfer.files[i];
      const url = this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(file));
      files.push({ file, url });
    }
    if (files.length > 0) {
      this.files.emit(files);
    }
  }
}
