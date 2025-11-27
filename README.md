# Weather Forecast App

OpenWeatherMap APIから天気情報を取得して表示する天気予報アプリ

## セットアップ

#### 1. **APIキーの設定**  

プロジェクトの`local.properties`にOpenWeatherMap ( https://openweathermap.org/ ) で発行したAPIキーを、以下の形式で追加してください。

   ```properties
   OPEN_WEATHER_MAP_API_KEY=your_api_key_here
   ```

※上記の設定を行わないと、APIからの天気取得が不可となり、エラー画面が表示されます。

#### 2. ビルドの実行


## 技術仕様

| 使用技術 | 用途 |
| --- | --- |
| Jetpack Compose | UI構築 |
| Navigation Compose | 画面遷移 |
| Retrofit | HTTP通信 |
| OkHttp(Logging Interceptor) | HTTP通信ログの取得 |
| Moshi | JSON → Kotlin データクラス変換 |
| Room | ローカルDB(ローカルキャッシュ) |
| Coil | 画像表示 |
| Google Play Services Location API | 位置情報の取得 |

## 画面仕様

### ホーム画面（HomeScreen）
- TopBar：タイトル
- UIコンポーネント
  - テキスト
  - リスト：「東京」「兵庫」「大分」「北海道」「現在地」
- 操作
  - リストの各アイテムタップで天気画面に遷移
### 天気画面（WeatherScreen）
- TopBar：戻るアイコン、タイトル
- UIコンポーネント
  - 【読み込み中】ローディング表示
  - 【成功時】リスト：（「日時」「天気アイコン」「予想気温」）の項目
  - 【失敗時】エラー表示
- 操作
  - 戻るアイコンタップでホーム画面に遷移

| ホーム画面 | 天気画面(成功時) |
| --- | --- |
| <img width="1080" height="2400" alt="Screenshot_20251126_220446" src="https://github.com/user-attachments/assets/ae44a7ac-5320-4801-9e2a-2d0f91dd3bd6" /> | <img width="1080" height="2400" alt="Screenshot_20251126_220454" src="https://github.com/user-attachments/assets/36bff696-5332-4ac5-ad64-98fbdd7bd533" /> |


| 天気画面(読み込み中) | 天気画面(失敗時) |
| --- | --- |
| <img width="1080" height="2400" alt="Screenshot_20251126_221401" src="https://github.com/user-attachments/assets/6f3eee52-df19-4789-91f9-33cee0bba810" /> | <img width="1080" height="2400" alt="Screenshot_20251126_221615" src="https://github.com/user-attachments/assets/9605cbbd-c0ee-4c90-8bb7-263d28e21400" /> |


## 機能仕様
#### 1. 天気情報表示
- OpenWeatherMap APIから天気予報を取得し表示
- 表示内容
  - 3時間ごとの天気予報（日時 / 天気アイコン / 気温）
- 天気アイコン画像はAsyncImageを用いて表示

#### 2. キャッシュ機能（Room）
- 都市名、APIレスポンス、取得日時をRoom Databaseに保存
- 同日/同地域への再リクエスト時はキャッシュを使用
  - ネットワーク不通時でもキャッシュがあれば表示可能

#### 3. 位置情報取得（任意）
- 「現在地」選択時、位置情報アクセス権限をリクエスト
  - 許可された場合、現在地の天気を表示可能とする
- Google Play Services Location API利用

#### 4. エラーハンドリング
- 天気情報の取得失敗時、エラーUI表示
- リトライボタンで再取得可能


### デモ動画

| 都市選択時の天気予報取得 | オフライン時のエラー表示→復帰 |
| --- | --- |
| <video src="https://github.com/user-attachments/assets/65183acc-1fbf-479e-8c3d-ae653f6e4177"> |  <video src="https://github.com/user-attachments/assets/2a13dd06-683c-48e2-942b-999f2a9d282b"> |



| オフライン時のキャッシュ利用（同日同地域） | 現在地選択時の権限リクエストと天気情報の取得 |
| --- | --- |
| <video src="https://github.com/user-attachments/assets/80cd81bd-47a2-4943-a2b6-fe3bfe157ef0"> |  <video src="https://github.com/user-attachments/assets/02bc7627-6a89-47d1-856d-15916d9db65b"> |


## 今後の展望
- コード品質の改善
  - 現状
    - 今回は時間の制約上、機能実装とアーキテクチャを優先し、ktlintの導入を見送った
    - そのためコードの一貫性における課題が残っている
  - 課題
    - Jetpack Compose固有のスタイルに合わせたカスタムルールの調査・検討に時間を要すると判断
  - 展望
    - Composeプロジェクト用のカスタムルールの定義及び実施
- セキュリティに関する改善
  - 現状
    - デバッグのためにOKHttp Logging Interceptor にて Body レベルのログを出力中
  - 展望
    - 必要に応じて出力有無や内容を検討
- UI/UX改善
  - ホーム画面、天気画面のUIの改善
  - 権限リクエストのフローの改善、エラーハンドリングの充実等
  - 画像表示のパフォーマンス改善
    - AsyncImageによる画像読み込み時のラグの改善または別の表示策の検討
  - 取得したレスポンスの加工と表示
    - 日付ごとにセクション化されたリスト表示への改善
  - キャッシュを削除する契機の追加
    - 案1：画面表示時に一括整理
    - 案2：ユーザーがデータリセットできるユースケースを追加
    - 案3：定期リフレッシュ
