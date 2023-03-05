imreimport os
import json
from pathlib import Path

_MSG_TEMPLATE = """
{msg_ja}

---

{msg_zh}
"""

def main():
  global _MSG_TEMPLATE
  with open("release_log.json", "r", encoding="utf8") as fp:
    log_list: list[dict] = json.load(fp)
  latest = log_list[0]
  _MSG_TEMPLATE = _MSG_TEMPLATE.replace(
      "{msg_ja}", latest["messageJa"])
  _MSG_TEMPLATE = _MSG_TEMPLATE.replace(
      "{msg_zh}", latest["messageZh"])

  Path("releaselog_cache.txt").write_text(_MSG_TEMPLATE, "utf8")
  # os.environ["RELEASE_APK_VERSION"] = latest["versionName"]

  with open("update_log.json", "w", encoding="utf8") as fp:
    json.dump(latest, fp, ensure_ascii=False, separators=(",", ":"))

  print(latest["versionName"])

if __name__ == "__main__":
  main()
