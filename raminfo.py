import psutil

memory = psutil.virtual_memory()

def convert_bytes(size):
    gb = size / (1024 ** 3)
    return round(gb, 2)

total_memory_gb = convert_bytes(memory.total)
available_memory_gb = convert_bytes(memory.available)
used_memory_gb = convert_bytes(memory.used)

print(f"Total Memory: {total_memory_gb} GB")
print(f"Available Memory: {available_memory_gb} GB")
print(f"Used Memory: {used_memory_gb} GB")      
print(f"RAM Usage: {memory.percent}%")