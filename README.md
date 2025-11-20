# yolov5 proyecto basado en https://github.com/spacewalk01/yolov5-face-mask-detection/blob/master/prepare.py

La intension de este proyecto es implementar en java la identificacion de cubreboca en rostro, usando el modelo de inferencia yolov5 entrenado por spacewalk01.

Primero he creado el modelo Onnx haciendo uso de colab.

Pasos usado para crear el modelo torchscript

1.- clonar el repositorio
!git clone https://github.com/ultralytics/yolov5
%cd yolov5

2.- instalar dependencias
!pip install -r requirements.txt

3.- exportando a torchscript
# Exportar a TorchScript usando el script de exportación de YOLOv5
!python export.py --weights mask_yolov5.pt --include torchscript

print("Model exported to mask_yolov5.torchscript.pt")
# The exported model will typically be named 'mask_yolov5.torchscript.pt' in the current directory.


##### exportando a Onnx
!python export.py --weights /content/yolov5/mask_yolov5.pt --include onnx


